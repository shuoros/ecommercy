import { Component, Vue, Inject } from 'vue-property-decorator';

import { IProductMainAttribute } from '@/shared/model/product-main-attribute.model';
import ProductMainAttributeService from './product-main-attribute.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class ProductMainAttributeDetails extends Vue {
  @Inject('productMainAttributeService') private productMainAttributeService: () => ProductMainAttributeService;
  @Inject('alertService') private alertService: () => AlertService;

  public productMainAttribute: IProductMainAttribute = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.productMainAttributeId) {
        vm.retrieveProductMainAttribute(to.params.productMainAttributeId);
      }
    });
  }

  public retrieveProductMainAttribute(productMainAttributeId) {
    this.productMainAttributeService()
      .find(productMainAttributeId)
      .then(res => {
        this.productMainAttribute = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
