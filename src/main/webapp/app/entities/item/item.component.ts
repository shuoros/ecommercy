import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IItem } from '@/shared/model/item.model';

import ItemService from './item.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Item extends Vue {
  @Inject('itemService') private itemService: () => ItemService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public items: IItem[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllItems();
  }

  public clear(): void {
    this.retrieveAllItems();
  }

  public retrieveAllItems(): void {
    this.isFetching = true;
    this.itemService()
      .retrieve()
      .then(
        res => {
          this.items = res.data;
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

  public prepareRemove(instance: IItem): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeItem(): void {
    this.itemService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ecommercyApp.item.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllItems();
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
