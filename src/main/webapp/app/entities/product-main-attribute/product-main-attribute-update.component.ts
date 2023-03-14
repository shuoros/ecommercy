import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import AttributeService from '@/entities/attribute/attribute.service';
import { IAttribute } from '@/shared/model/attribute.model';

import ProductService from '@/entities/product/product.service';
import { IProduct } from '@/shared/model/product.model';

import { IProductMainAttribute, ProductMainAttribute } from '@/shared/model/product-main-attribute.model';
import ProductMainAttributeService from './product-main-attribute.service';

const validations: any = {
  productMainAttribute: {
    price: {},
    discount: {},
  },
};

@Component({
  validations,
})
export default class ProductMainAttributeUpdate extends Vue {
  @Inject('productMainAttributeService') private productMainAttributeService: () => ProductMainAttributeService;
  @Inject('alertService') private alertService: () => AlertService;

  public productMainAttribute: IProductMainAttribute = new ProductMainAttribute();

  @Inject('attributeService') private attributeService: () => AttributeService;

  public attributes: IAttribute[] = [];

  @Inject('productService') private productService: () => ProductService;

  public products: IProduct[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.productMainAttributeId) {
        vm.retrieveProductMainAttribute(to.params.productMainAttributeId);
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
    if (this.productMainAttribute.id) {
      this.productMainAttributeService()
        .update(this.productMainAttribute)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ecommercyApp.productMainAttribute.updated', { param: param.id });
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
      this.productMainAttributeService()
        .create(this.productMainAttribute)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ecommercyApp.productMainAttribute.created', { param: param.id });
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

  public retrieveProductMainAttribute(productMainAttributeId): void {
    this.productMainAttributeService()
      .find(productMainAttributeId)
      .then(res => {
        this.productMainAttribute = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.attributeService()
      .retrieve()
      .then(res => {
        this.attributes = res.data;
      });
    this.productService()
      .retrieve()
      .then(res => {
        this.products = res.data;
      });
  }
}
