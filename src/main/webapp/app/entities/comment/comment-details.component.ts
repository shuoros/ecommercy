import { Component, Vue, Inject } from 'vue-property-decorator';

import { IComment } from '@/shared/model/comment.model';
import CommentService from './comment.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class CommentDetails extends Vue {
  @Inject('commentService') private commentService: () => CommentService;
  @Inject('alertService') private alertService: () => AlertService;

  public comment: IComment = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.commentId) {
        vm.retrieveComment(to.params.commentId);
      }
    });
  }

  public retrieveComment(commentId) {
    this.commentService()
      .find(commentId)
      .then(res => {
        this.comment = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
