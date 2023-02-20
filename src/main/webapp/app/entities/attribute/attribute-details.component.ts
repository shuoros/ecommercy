import { Component, Vue, Inject } from 'vue-property-decorator';

import { IAttribute } from '@/shared/model/attribute.model';
import AttributeService from './attribute.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class AttributeDetails extends Vue {
  @Inject('attributeService') private attributeService: () => AttributeService;
  @Inject('alertService') private alertService: () => AlertService;

  public attribute: IAttribute = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.attributeId) {
        vm.retrieveAttribute(to.params.attributeId);
      }
    });
  }

  public retrieveAttribute(attributeId) {
    this.attributeService()
      .find(attributeId)
      .then(res => {
        this.attribute = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
