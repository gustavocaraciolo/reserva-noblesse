import { mixins } from 'vue-class-component';

import { Component, Vue, Inject } from 'vue-property-decorator';
import Vue2Filters from 'vue2-filters';
import { IApartamento } from '@/shared/model/apartamento.model';

import ApartamentoService from './apartamento.service';

@Component({
  mixins: [Vue2Filters.mixin],
})
export default class Apartamento extends Vue {
  @Inject('apartamentoService') private apartamentoService: () => ApartamentoService;
  private removeId: number = null;

  public apartamentos: IApartamento[] = [];

  public isFetching = false;

  public mounted(): void {
    this.retrieveAllApartamentos();
  }

  public clear(): void {
    this.retrieveAllApartamentos();
  }

  public retrieveAllApartamentos(): void {
    this.isFetching = true;
    this.apartamentoService()
      .retrieve()
      .then(
        res => {
          this.apartamentos = res.data;
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

  public prepareRemove(instance: IApartamento): void {
    this.removeId = instance.id;
    if (<any>this.$refs.removeEntity) {
      (<any>this.$refs.removeEntity).show();
    }
  }

  public removeApartamento(): void {
    this.apartamentoService()
      .delete(this.removeId)
      .then(() => {
        const message = this.$t('reservaNoblesseApp.apartamento.deleted', { param: this.removeId });
        this.$bvToast.toast(message.toString(), {
          toaster: 'b-toaster-top-center',
          title: 'Info',
          variant: 'danger',
          solid: true,
          autoHideDelay: 5000,
        });
        this.removeId = null;
        this.retrieveAllApartamentos();
        this.closeDialog();
      });
  }

  public closeDialog(): void {
    (<any>this.$refs.removeEntity).hide();
  }
}
