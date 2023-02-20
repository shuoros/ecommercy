import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IBasket } from '@/shared/model/basket.model';

import BasketService from './basket.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Basket extends Vue {
  @Inject('basketService') private basketService: () => BasketService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public baskets: IBasket[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllBaskets();
  }

  public clear(): void {
    this.retrieveAllBaskets();
  }

  public retrieveAllBaskets(): void {
    this.isFetching = true;
    this.basketService()
      .retrieve()
      .then(
        res => {
          this.baskets = res.data;
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

  public prepareRemove(instance: IBasket): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeBasket(): void {
    this.basketService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ecommercyApp.basket.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllBaskets();
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
