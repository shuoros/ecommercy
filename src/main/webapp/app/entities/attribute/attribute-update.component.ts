import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import ProductMainAttributeService from '@/entities/product-main-attribute/product-main-attribute.service';
import { IProductMainAttribute } from '@/shared/model/product-main-attribute.model';

import ProductService from '@/entities/product/product.service';
import { IProduct } from '@/shared/model/product.model';

import { IAttribute, Attribute } from '@/shared/model/attribute.model';
import AttributeService from './attribute.service';

const validations: any = {
  attribute: {
    key: {},
    value: {},
  },
};

@Component({
  validations,
})
export default class AttributeUpdate extends Vue {
  @Inject('attributeService') private attributeService: () => AttributeService;
  @Inject('alertService') private alertService: () => AlertService;

  public attribute: IAttribute = new Attribute();

  @Inject('productMainAttributeService') private productMainAttributeService: () => ProductMainAttributeService;

  public productMainAttributes: IProductMainAttribute[] = [];

  @Inject('productService') private productService: () => ProductService;

  public products: IProduct[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.attributeId) {
        vm.retrieveAttribute(to.params.attributeId);
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
    if (this.attribute.id) {
      this.attributeService()
        .update(this.attribute)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ecommercyApp.attribute.updated', { param: param.id });
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
      this.attributeService()
        .create(this.attribute)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ecommercyApp.attribute.created', { param: param.id });
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

  public retrieveAttribute(attributeId): void {
    this.attributeService()
      .find(attributeId)
      .then(res => {
        this.attribute = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.productMainAttributeService()
      .retrieve()
      .then(res => {
        this.productMainAttributes = res.data;
      });
    this.productService()
      .retrieve()
      .then(res => {
        this.products = res.data;
      });
  }
}
