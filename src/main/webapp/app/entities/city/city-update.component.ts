import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import StateService from '@/entities/state/state.service';
import { IState } from '@/shared/model/state.model';

import { ICity, City } from '@/shared/model/city.model';
import CityService from './city.service';

const validations: any = {
  city: {
    name: {},
  },
};

@Component({
  validations,
})
export default class CityUpdate extends Vue {
  @Inject('cityService') private cityService: () => CityService;
  @Inject('alertService') private alertService: () => AlertService;

  public city: ICity = new City();

  @Inject('stateService') private stateService: () => StateService;

  public states: IState[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cityId) {
        vm.retrieveCity(to.params.cityId);
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
    if (this.city.id) {
      this.cityService()
        .update(this.city)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ecommercyApp.city.updated', { param: param.id });
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
      this.cityService()
        .create(this.city)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ecommercyApp.city.created', { param: param.id });
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

  public retrieveCity(cityId): void {
    this.cityService()
      .find(cityId)
      .then(res => {
        this.city = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.stateService()
      .retrieve()
      .then(res => {
        this.states = res.data;
      });
  }
}
