import { Component, Vue, Inject } from 'vue-property-decorator';

import { IBasket } from '@/shared/model/basket.model';
import BasketService from './basket.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class BasketDetails extends Vue {
  @Inject('basketService') private basketService: () => BasketService;
  @Inject('alertService') private alertService: () => AlertService;

  public basket: IBasket = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.basketId) {
        vm.retrieveBasket(to.params.basketId);
      }
    });
  }

  public retrieveBasket(basketId) {
    this.basketService()
      .find(basketId)
      .then(res => {
        this.basket = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
