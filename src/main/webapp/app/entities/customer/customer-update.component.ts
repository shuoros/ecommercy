import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import UserService from '@/entities/user/user.service';

import BasketService from '@/entities/basket/basket.service';
import { IBasket } from '@/shared/model/basket.model';

import CommentService from '@/entities/comment/comment.service';
import { IComment } from '@/shared/model/comment.model';

import AddressService from '@/entities/address/address.service';
import { IAddress } from '@/shared/model/address.model';

import CouponService from '@/entities/coupon/coupon.service';
import { ICoupon } from '@/shared/model/coupon.model';

import { ICustomer, Customer } from '@/shared/model/customer.model';
import CustomerService from './customer.service';

const validations: any = {
  customer: {
    score: {},
  },
};

@Component({
  validations,
})
export default class CustomerUpdate extends Vue {
  @Inject('customerService') private customerService: () => CustomerService;
  @Inject('alertService') private alertService: () => AlertService;

  public customer: ICustomer = new Customer();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];

  @Inject('basketService') private basketService: () => BasketService;

  public baskets: IBasket[] = [];

  @Inject('commentService') private commentService: () => CommentService;

  public comments: IComment[] = [];

  @Inject('addressService') private addressService: () => AddressService;

  public addresses: IAddress[] = [];

  @Inject('couponService') private couponService: () => CouponService;

  public coupons: ICoupon[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.customerId) {
        vm.retrieveCustomer(to.params.customerId);
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
    if (this.customer.id) {
      this.customerService()
        .update(this.customer)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ecommercyApp.customer.updated', { param: param.id });
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
      this.customerService()
        .create(this.customer)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ecommercyApp.customer.created', { param: param.id });
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

  public retrieveCustomer(customerId): void {
    this.customerService()
      .find(customerId)
      .then(res => {
        this.customer = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
    this.basketService()
      .retrieve()
      .then(res => {
        this.baskets = res.data;
      });
    this.commentService()
      .retrieve()
      .then(res => {
        this.comments = res.data;
      });
    this.addressService()
      .retrieve()
      .then(res => {
        this.addresses = res.data;
      });
    this.couponService()
      .retrieve()
      .then(res => {
        this.coupons = res.data;
      });
  }
}
