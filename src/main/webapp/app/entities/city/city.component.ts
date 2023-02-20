import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ICity } from '@/shared/model/city.model';

import CityService from './city.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class City extends Vue {
  @Inject('cityService') private cityService: () => CityService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public cities: ICity[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllCitys();
  }

  public clear(): void {
    this.retrieveAllCitys();
  }

  public retrieveAllCitys(): void {
    this.isFetching = true;
    this.cityService()
      .retrieve()
      .then(
        res => {
          this.cities = res.data;
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

  public prepareRemove(instance: ICity): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeCity(): void {
    this.cityService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ecommercyApp.city.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllCitys();
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
