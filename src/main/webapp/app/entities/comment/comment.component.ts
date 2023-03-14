import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IComment } from '@/shared/model/comment.model';

import CommentService from './comment.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Comment extends Vue {
  @Inject('commentService') private commentService: () => CommentService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public comments: IComment[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllComments();
  }

  public clear(): void {
    this.retrieveAllComments();
  }

  public retrieveAllComments(): void {
    this.isFetching = true;
    this.commentService()
      .retrieve()
      .then(
        res => {
          this.comments = res.data;
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

  public prepareRemove(instance: IComment): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeComment(): void {
    this.commentService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ecommercyApp.comment.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllComments();
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
