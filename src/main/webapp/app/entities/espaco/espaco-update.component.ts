import { Component, Vue, Inject } from 'vue-property-decorator';

import { maxLength } from 'vuelidate/lib/validators';

import ReservaService from '@/entities/reserva/reserva.service';
import { IReserva } from '@/shared/model/reserva.model';

import { IEspaco, Espaco } from '@/shared/model/espaco.model';
import EspacoService from './espaco.service';

const validations: any = {
  espaco: {
    nome: {
      maxLength: maxLength(140),
    },
  },
};

@Component({
  validations,
})
export default class EspacoUpdate extends Vue {
  @Inject('espacoService') private espacoService: () => EspacoService;
  public espaco: IEspaco = new Espaco();

  @Inject('reservaService') private reservaService: () => ReservaService;

  public reservas: IReserva[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.espacoId) {
        vm.retrieveEspaco(to.params.espacoId);
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
    if (this.espaco.id) {
      this.espacoService()
        .update(this.espaco)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('reservaNoblesseApp.espaco.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.espacoService()
        .create(this.espaco)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('reservaNoblesseApp.espaco.created', { param: param.id });
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

  public retrieveEspaco(espacoId): void {
    this.espacoService()
      .find(espacoId)
      .then(res => {
        this.espaco = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.reservaService()
      .retrieve()
      .then(res => {
        this.reservas = res.data;
      });
  }
}
