import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICoupon } from '@/shared/model/coupon.model';
import CouponService from './coupon.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CouponDetails extends Vue {
  @Inject('couponService') private couponService: () => CouponService;
  @Inject('alertService') private alertService: () => AlertService;

  public coupon: ICoupon = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.couponId) {
        vm.retrieveCoupon(to.params.couponId);
      }
    });
  }

  public retrieveCoupon(couponId) {
    this.couponService()
      .find(couponId)
      .then(res => {
        this.coupon = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
