import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IProductMainAttribute } from '@/shared/model/product-main-attribute.model';

import ProductMainAttributeService from './product-main-attribute.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class ProductMainAttribute extends Vue {
  @Inject('productMainAttributeService') private productMainAttributeService: () => ProductMainAttributeService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public productMainAttributes: IProductMainAttribute[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllProductMainAttributes();
  }

  public clear(): void {
    this.retrieveAllProductMainAttributes();
  }

  public retrieveAllProductMainAttributes(): void {
    this.isFetching = true;
    this.productMainAttributeService()
      .retrieve()
      .then(
        res => {
          this.productMainAttributes = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IProductMainAttribute): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeProductMainAttribute(): void {
    this.productMainAttributeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ecommercyApp.productMainAttribute.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllProductMainAttributes();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
