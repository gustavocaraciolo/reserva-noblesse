import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { ITorre } from '@/shared/model/torre.model';

import TorreService from './torre.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Torre extends Vue {
  @Inject('torreService') private torreService: () => TorreService;
  private removeId: number = null;

  public torres: ITorre[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllTorres();
  }

  public clear(): void {
    this.retrieveAllTorres();
  }

  public retrieveAllTorres(): void {
    this.isFetching = true;
    this.torreService()
      .retrieve()
      .then(
        res => {
          this.torres = res.data;
          this.isFetching = false;
        },
        err => {
          this.isFetching = false;
        }
      );
  }

  public handleSyncList(): void {
    this.clear();
  }

  public prepareRemove(instance: ITorre): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeTorre(): void {
    this.torreService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('reservaNoblesseApp.torre.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllTorres();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
