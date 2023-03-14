import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IAttribute } from '@/shared/model/attribute.model';

import AttributeService from './attribute.service';
import AlertService from '@/shared/alert/alert.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Attribute extends Vue {
  @Inject('attributeService') private attributeService: () => AttributeService;
  @Inject('alertService') private alertService: () => AlertService;

  private removeId: number = null;

  public attributes: IAttribute[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllAttributes();
  }

  public clear(): void {
    this.retrieveAllAttributes();
  }

  public retrieveAllAttributes(): void {
    this.isFetching = true;
    this.attributeService()
      .retrieve()
      .then(
        res => {
          this.attributes = res.data;
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

  public prepareRemove(instance: IAttribute): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeAttribute(): void {
    this.attributeService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('ecommercyApp.attribute.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllAttributes();
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
