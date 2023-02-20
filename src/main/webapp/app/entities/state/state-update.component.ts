import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import CityService from '@/entities/city/city.service';
import { ICity } from '@/shared/model/city.model';

import CountryService from '@/entities/country/country.service';
import { ICountry } from '@/shared/model/country.model';

import { IState, State } from '@/shared/model/state.model';
import StateService from './state.service';

const validations: any = {
  state: {
    name: {},
  },
};

@Component({
  validations,
})
export default class StateUpdate extends Vue {
  @Inject('stateService') private stateService: () => StateService;
  @Inject('alertService') private alertService: () => AlertService;

  public state: IState = new State();

  @Inject('cityService') private cityService: () => CityService;

  public cities: ICity[] = [];

  @Inject('countryService') private countryService: () => CountryService;

  public countries: ICountry[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stateId) {
        vm.retrieveState(to.params.stateId);
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
    if (this.state.id) {
      this.stateService()
        .update(this.state)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ecommercyApp.state.updated', { param: param.id });
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
      this.stateService()
        .create(this.state)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ecommercyApp.state.created', { param: param.id });
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

  public retrieveState(stateId): void {
    this.stateService()
      .find(stateId)
      .then(res => {
        this.state = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.cityService()
      .retrieve()
      .then(res => {
        this.cities = res.data;
      });
    this.countryService()
      .retrieve()
      .then(res => {
        this.countries = res.data;
      });
  }
}
