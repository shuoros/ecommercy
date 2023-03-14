import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IGroup } from '@/shared/model/group.model';

import GroupService from './group.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Group extends Vue {
  @Inject('groupService') private groupService: () => GroupService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public groups: IGroup[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllGroups();
  }

  public clear(): void {
    this.retrieveAllGroups();
  }

  public retrieveAllGroups(): void {
    this.isFetching = true;
    this.groupService()
      .retrieve()
      .then(
        res => {
          this.groups = res.data;
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

  public prepareRemove(instance: IGroup): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeGroup(): void {
    this.groupService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ecommercyApp.group.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllGroups();
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
