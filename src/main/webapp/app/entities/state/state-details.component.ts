import { Component, Vue, Inject } from 'vue-property-decorator';

import { IState } from '@/shared/model/state.model';
import StateService from './state.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class StateDetails extends Vue {
  @Inject('stateService') private stateService: () => StateService;
  @Inject('alertService') private alertService: () => AlertService;

  public state: IState = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.stateId) {
        vm.retrieveState(to.params.stateId);
      }
    });
  }

  public retrieveState(stateId) {
    this.stateService()
      .find(stateId)
      .then(res => {
        this.state = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
