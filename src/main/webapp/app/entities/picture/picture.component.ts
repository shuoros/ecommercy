import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IPicture } from '@/shared/model/picture.model';

import PictureService from './picture.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Picture extends Vue {
  @Inject('pictureService') private pictureService: () => PictureService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public pictures: IPicture[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllPictures();
  }

  public clear(): void {
    this.retrieveAllPictures();
  }

  public retrieveAllPictures(): void {
    this.isFetching = true;
    this.pictureService()
      .retrieve()
      .then(
        res => {
          this.pictures = res.data;
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

  public prepareRemove(instance: IPicture): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removePicture(): void {
    this.pictureService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ecommercyApp.picture.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllPictures();
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
