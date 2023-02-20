import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAddress } from '@/shared/model/address.model';

import AddressService from './address.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Address extends Vue {
  @Inject('addressService') private addressService: () => AddressService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public addresses: IAddress[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAddresss();
  }

  public clear(): void {
    this.retrieveAllAddresss();
  }

  public retrieveAllAddresss(): void {
    this.isFetching = true;
    this.addressService()
      .retrieve()
      .then(
        res => {
          this.addresses = res.data;
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

  public prepareRemove(instance: IAddress): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAddress(): void {
    this.addressService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ecommercyApp.address.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllAddresss();
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
