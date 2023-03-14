import { Component, Vue, Inject } from 'vue-property-decorator';

import { IOrder } from '@/shared/model/order.model';
import OrderService from './order.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class OrderDetails extends Vue {
  @Inject('orderService') private orderService: () => OrderService;
  @Inject('alertService') private alertService: () => AlertService;

  public order: IOrder = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.orderId) {
        vm.retrieveOrder(to.params.orderId);
      }
    });
  }

  public retrieveOrder(orderId) {
    this.orderService()
      .find(orderId)
      .then(res => {
        this.order = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
