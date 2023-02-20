import { Component, Vue, Inject } from 'vue-property-decorator';

import { IGroup } from '@/shared/model/group.model';
import GroupService from './group.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class GroupDetails extends Vue {
  @Inject('groupService') private groupService: () => GroupService;
  @Inject('alertService') private alertService: () => AlertService;

  public group: IGroup = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.groupId) {
        vm.retrieveGroup(to.params.groupId);
      }
    });
  }

  public retrieveGroup(groupId) {
    this.groupService()
      .find(groupId)
      .then(res => {
        this.group = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
