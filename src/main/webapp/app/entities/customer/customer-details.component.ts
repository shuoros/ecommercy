import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICustomer } from '@/shared/model/customer.model';
import CustomerService from './customer.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CustomerDetails extends Vue {
  @Inject('customerService') private customerService: () => CustomerService;
  @Inject('alertService') private alertService: () => AlertService;

  public customer: ICustomer = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.customerId) {
        vm.retrieveCustomer(to.params.customerId);
      }
    });
  }

  public retrieveCustomer(customerId) {
    this.customerService()
      .find(customerId)
      .then(res => {
        this.customer = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
