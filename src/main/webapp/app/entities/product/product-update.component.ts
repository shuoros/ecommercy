import { Component, Vue, Inject } from 'vue-property-decorator';

import AlertService from '@/shared/alert/alert.service';

import PictureService from '@/entities/picture/picture.service';
import { IPicture } from '@/shared/model/picture.model';

import ProductMainAttributeService from '@/entities/product-main-attribute/product-main-attribute.service';
import { IProductMainAttribute } from '@/shared/model/product-main-attribute.model';

import CommentService from '@/entities/comment/comment.service';
import { IComment } from '@/shared/model/comment.model';

import AttributeService from '@/entities/attribute/attribute.service';
import { IAttribute } from '@/shared/model/attribute.model';

import ItemService from '@/entities/item/item.service';
import { IItem } from '@/shared/model/item.model';

import CategoryService from '@/entities/category/category.service';
import { ICategory } from '@/shared/model/category.model';

import { IProduct, Product } from '@/shared/model/product.model';
import ProductService from './product.service';

const validations: any = {
  product: {
    name: {},
    brand: {},
    rate: {},
    description: {},
  },
};

@Component({
  validations,
})
export default class ProductUpdate extends Vue {
  @Inject('productService') private productService: () => ProductService;
  @Inject('alertService') private alertService: () => AlertService;

  public product: IProduct = new Product();

  @Inject('pictureService') private pictureService: () => PictureService;

  public pictures: IPicture[] = [];

  @Inject('productMainAttributeService') private productMainAttributeService: () => ProductMainAttributeService;

  public productMainAttributes: IProductMainAttribute[] = [];

  @Inject('commentService') private commentService: () => CommentService;

  public comments: IComment[] = [];

  @Inject('attributeService') private attributeService: () => AttributeService;

  public attributes: IAttribute[] = [];

  @Inject('itemService') private itemService: () => ItemService;

  public items: IItem[] = [];

  @Inject('categoryService') private categoryService: () => CategoryService;

  public categories: ICategory[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.productId) {
        vm.retrieveProduct(to.params.productId);
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
    this.product.attributes = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.product.id) {
      this.productService()
        .update(this.product)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ecommercyApp.product.updated', { param: param.id });
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
      this.productService()
        .create(this.product)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('ecommercyApp.product.created', { param: param.id });
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

  public retrieveProduct(productId): void {
    this.productService()
      .find(productId)
      .then(res => {
        this.product = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.pictureService()
      .retrieve()
      .then(res => {
        this.pictures = res.data;
      });
    this.productMainAttributeService()
      .retrieve()
      .then(res => {
        this.productMainAttributes = res.data;
      });
    this.commentService()
      .retrieve()
      .then(res => {
        this.comments = res.data;
      });
    this.attributeService()
      .retrieve()
      .then(res => {
        this.attributes = res.data;
      });
    this.itemService()
      .retrieve()
      .then(res => {
        this.items = res.data;
      });
    this.categoryService()
      .retrieve()
      .then(res => {
        this.categories = res.data;
      });
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      return selectedVals.find(value => option.id === value.id) ?? option;
    }
    return option;
  }
}
