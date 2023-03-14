import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICity } from '@/shared/model/city.model';
import CityService from './city.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CityDetails extends Vue {
  @Inject('cityService') private cityService: () => CityService;
  @Inject('alertService') private alertService: () => AlertService;

  public city: ICity = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.cityId) {
        vm.retrieveCity(to.params.cityId);
      }
    });
  }

  public retrieveCity(cityId) {
    this.cityService()
      .find(cityId)
      .then(res => {
        this.city = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
