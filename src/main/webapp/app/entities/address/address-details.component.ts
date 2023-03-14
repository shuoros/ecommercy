import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAddress } from '@/shared/model/address.model';
import AddressService from './address.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class AddressDetails extends Vue {
  @Inject('addressService') private addressService: () => AddressService;
  @Inject('alertService') private alertService: () => AlertService;

  public address: IAddress = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.addressId) {
        vm.retrieveAddress(to.params.addressId);
      }
    });
  }

  public retrieveAddress(addressId) {
    this.addressService()
      .find(addressId)
      .then(res => {
        this.address = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
