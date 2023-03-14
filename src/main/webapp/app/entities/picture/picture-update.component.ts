import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import ProductService from '@/entities/product/product.service';
import { IProduct } from '@/shared/model/product.model';

import { IPicture, Picture } from '@/shared/model/picture.model';
import PictureService from './picture.service';

const validations: any = {
  picture: {
    path: {},
  },
};

@Component({
  validations,
})
export default class PictureUpdate extends Vue {
  @Inject('pictureService') private pictureService: () => PictureService;
  @Inject('alertService') private alertService: () => AlertService;

  public picture: IPicture = new Picture();

  @Inject('productService') private productService: () => ProductService;

  public products: IProduct[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.pictureId) {
        vm.retrievePicture(to.params.pictureId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.picture.id) {
      this.pictureService()
        .update(this.picture)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ecommercyApp.picture.updated', { param: param.id });
          return (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    } else {
      this.pictureService()
        .create(this.picture)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ecommercyApp.picture.created', { param: param.id });
          (this.$root as any).$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        })
        .catch(error => {
          this.isSaving = false;
          this.alertService().showHttpError(this, error.response);
        });
    }
  }

  public retrievePicture(pictureId): void {
    this.pictureService()
      .find(pictureId)
      .then(res => {
        this.picture = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.productService()
      .retrieve()
      .then(res => {
        this.products = res.data;
      });
  }
}
