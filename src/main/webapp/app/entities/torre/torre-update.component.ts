import { Component, Vue, Inject } from 'vue-property-decorator';

import { maxLength } from 'vuelidate/lib/validators';

import ApartamentoService from '@/entities/apartamento/apartamento.service';
import { IApartamento } from '@/shared/model/apartamento.model';

import { ITorre, Torre } from '@/shared/model/torre.model';
import TorreService from './torre.service';

const validations: any = {
  torre: {
    nome: {
      maxLength: maxLength(140),
    },
  },
};

@Component({
  validations,
})
export default class TorreUpdate extends Vue {
  @Inject('torreService') private torreService: () => TorreService;
  public torre: ITorre = new Torre();

  @Inject('apartamentoService') private apartamentoService: () => ApartamentoService;

  public apartamentos: IApartamento[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.torreId) {
        vm.retrieveTorre(to.params.torreId);
      }
      vm.initRelationships();
    });
  }

  created(): void {
    this.currentLanguage = this.$store.getters.currentLanguage;
    this.$store.watch(
      () => this.$store.getters.currentLanguage,
      () => {
        this.currentLanguage = this.$store.getters.currentLanguage;
      }
    );
  }

  public save(): void {
    this.isSaving = true;
    if (this.torre.id) {
      this.torreService()
        .update(this.torre)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('reservaNoblesseApp.torre.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.torreService()
        .create(this.torre)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('reservaNoblesseApp.torre.created', { param: param.id });
          this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public retrieveTorre(torreId): void {
    this.torreService()
      .find(torreId)
      .then(res => {
        this.torre = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.apartamentoService()
      .retrieve()
      .then(res => {
        this.apartamentos = res.data;
      });
  }
}
