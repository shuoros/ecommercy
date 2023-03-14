import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IState } from '@/shared/model/state.model';

import StateService from './state.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class State extends Vue {
  @Inject('stateService') private stateService: () => StateService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public states: IState[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllStates();
  }

  public clear(): void {
    this.retrieveAllStates();
  }

  public retrieveAllStates(): void {
    this.isFetching = true;
    this.stateService()
      .retrieve()
      .then(
        res => {
          this.states = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
          this.alertService().showHttpError(this, err.response);
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: IState): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeState(): void {
    this.stateService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ecommercyApp.state.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllStates();
        this.closeDialog();
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
