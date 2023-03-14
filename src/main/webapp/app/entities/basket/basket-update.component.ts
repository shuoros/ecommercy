import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import OrderService from '@/entities/order/order.service';
import { IOrder } from '@/shared/model/order.model';

import ItemService from '@/entities/item/item.service';
import { IItem } from '@/shared/model/item.model';

import CustomerService from '@/entities/customer/customer.service';
import { ICustomer } from '@/shared/model/customer.model';

import { IBasket, Basket } from '@/shared/model/basket.model';
import BasketService from './basket.service';

const validations: any = {
  basket: {
    score: {},
    price: {},
  },
};

@Component({
  validations,
})
export default class BasketUpdate extends Vue {
  @Inject('basketService') private basketService: () => BasketService;
  @Inject('alertService') private alertService: () => AlertService;

  public basket: IBasket = new Basket();

  @Inject('orderService') private orderService: () => OrderService;

  public orders: IOrder[] = [];

  @Inject('itemService') private itemService: () => ItemService;

  public items: IItem[] = [];

  @Inject('customerService') private customerService: () => CustomerService;

  public customers: ICustomer[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.basketId) {
        vm.retrieveBasket(to.params.basketId);
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
    if (this.basket.id) {
      this.basketService()
        .update(this.basket)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ecommercyApp.basket.updated', { param: param.id });
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
      this.basketService()
        .create(this.basket)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ecommercyApp.basket.created', { param: param.id });
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

  public retrieveBasket(basketId): void {
    this.basketService()
      .find(basketId)
      .then(res => {
        this.basket = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.orderService()
      .retrieve()
      .then(res => {
        this.orders = res.data;
      });
    this.itemService()
      .retrieve()
      .then(res => {
        this.items = res.data;
      });
    this.customerService()
      .retrieve()
      .then(res => {
        this.customers = res.data;
      });
  }
}
