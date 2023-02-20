import { Component, Vue, Inject } from 'vue-property-decorator';

import { ICategory } from '@/shared/model/category.model';
import CategoryService from './category.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CategoryDetails extends Vue {
  @Inject('categoryService') private categoryService: () => CategoryService;
  @Inject('alertService') private alertService: () => AlertService;

  public category: ICategory = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.categoryId) {
        vm.retrieveCategory(to.params.categoryId);
      }
    });
  }

  public retrieveCategory(categoryId) {
    this.categoryService()
      .find(categoryId)
      .then(res => {
        this.category = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
