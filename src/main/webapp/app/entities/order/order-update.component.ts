import { Component, Vue, Inject } from 'vue-property-decorator';

import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import AlertService from '@/shared/alert/alert.service';

import AddressService from '@/entities/address/address.service';
import { IAddress } from '@/shared/model/address.model';

import CouponService from '@/entities/coupon/coupon.service';
import { ICoupon } from '@/shared/model/coupon.model';

import BasketService from '@/entities/basket/basket.service';
import { IBasket } from '@/shared/model/basket.model';

import { IOrder, Order } from '@/shared/model/order.model';
import OrderService from './order.service';
import { OrderStatus } from '@/shared/model/enumerations/order-status.model';

const validations: any = {
  order: {
    status: {},
    receive: {},
  },
};

@Component({
  validations,
})
export default class OrderUpdate extends Vue {
  @Inject('orderService') private orderService: () => OrderService;
  @Inject('alertService') private alertService: () => AlertService;

  public order: IOrder = new Order();

  @Inject('addressService') private addressService: () => AddressService;

  public addresses: IAddress[] = [];

  @Inject('couponService') private couponService: () => CouponService;

  public coupons: ICoupon[] = [];

  @Inject('basketService') private basketService: () => BasketService;

  public baskets: IBasket[] = [];
  public orderStatusValues: string[] = Object.keys(OrderStatus);
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.orderId) {
        vm.retrieveOrder(to.params.orderId);
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
    if (this.order.id) {
      this.orderService()
        .update(this.order)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ecommercyApp.order.updated', { param: param.id });
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
      this.orderService()
        .create(this.order)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ecommercyApp.order.created', { param: param.id });
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

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.order[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.order[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.order[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.order[field] = null;
    }
  }

  public retrieveOrder(orderId): void {
    this.orderService()
      .find(orderId)
      .then(res => {
        res.receive = new Date(res.receive);
        this.order = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
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
    this.basketService()
      .retrieve()
      .then(res => {
        this.baskets = res.data;
      });
  }
}
