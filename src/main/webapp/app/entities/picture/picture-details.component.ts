import { Component, Vue, Inject } from 'vue-property-decorator';

import { IPicture } from '@/shared/model/picture.model';
import PictureService from './picture.service';
import AlertService from '@/shared/alert/alert.service';

@Component
export default class PictureDetails extends Vue {
  @Inject('pictureService') private pictureService: () => PictureService;
  @Inject('alertService') private alertService: () => AlertService;

  public picture: IPicture = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.pictureId) {
        vm.retrievePicture(to.params.pictureId);
      }
    });
  }

  public retrievePicture(pictureId) {
    this.pictureService()
      .find(pictureId)
      .then(res => {
        this.picture = res;
      })
      .catch(error => {
        this.alertService().showHttpError(this, error.response);
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
