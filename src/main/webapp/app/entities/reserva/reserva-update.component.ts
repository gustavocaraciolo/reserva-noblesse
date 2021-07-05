import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, maxLength } from 'vuelidate/lib/validators';

import UserService from '@/admin/user-management/user-management.service';

import EspacoService from '@/entities/espaco/espaco.service';
import { IEspaco } from '@/shared/model/espaco.model';

import { IReserva, Reserva } from '@/shared/model/reserva.model';
import ReservaService from './reserva.service';

const validations: any = {
  reserva: {
    date: {
      required,
    },
    notes: {
      maxLength: maxLength(140),
    },
  },
};

@Component({
  validations,
})
export default class ReservaUpdate extends Vue {
  @Inject('reservaService') private reservaService: () => ReservaService;
  public reserva: IReserva = new Reserva();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];

  @Inject('espacoService') private espacoService: () => EspacoService;

  public espacos: IEspaco[] = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.reservaId) {
        vm.retrieveReserva(to.params.reservaId);
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
    this.reserva.espacos = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.reserva.id) {
      this.reservaService()
        .update(this.reserva)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('reservaNoblesseApp.reserva.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.reservaService()
        .create(this.reserva)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('reservaNoblesseApp.reserva.created', { param: param.id });
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

  public retrieveReserva(reservaId): void {
    this.reservaService()
      .find(reservaId)
      .then(res => {
        this.reserva = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
    this.espacoService()
      .retrieve()
      .then(res => {
        this.espacos = res.data;
      });
  }

  public getSelected(selectedVals, option): any {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
