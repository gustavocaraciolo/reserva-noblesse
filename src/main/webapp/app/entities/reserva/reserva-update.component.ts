import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, maxLength } from 'vuelidate/lib/validators';
import dayjs from 'dayjs';
import { DATE_TIME_LONG_FORMAT } from '@/shared/date/filters';

import EspacoService from '@/entities/espaco/espaco.service';
import { IEspaco } from '@/shared/model/espaco.model';

import UserService from '@/admin/user-management/user-management.service';

import { IReserva, Reserva } from '@/shared/model/reserva.model';
import ReservaService from './reserva.service';

const validations: any = {
  reserva: {
    dataHora: {
      required,
    },
    notas: {
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

  @Inject('espacoService') private espacoService: () => EspacoService;

  public espacos: IEspaco[] = [];

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];
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

  public convertDateTimeFromServer(date: Date): string {
    if (date && dayjs(date).isValid()) {
      return dayjs(date).format(DATE_TIME_LONG_FORMAT);
    }
    return null;
  }

  public updateInstantField(field, event) {
    if (event.target.value) {
      this.reserva[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.reserva[field] = null;
    }
  }

  public updateZonedDateTimeField(field, event) {
    if (event.target.value) {
      this.reserva[field] = dayjs(event.target.value, DATE_TIME_LONG_FORMAT);
    } else {
      this.reserva[field] = null;
    }
  }

  public retrieveReserva(reservaId): void {
    this.reservaService()
      .find(reservaId)
      .then(res => {
        res.dataHora = new Date(res.dataHora);
        this.reserva = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.espacoService()
      .retrieve()
      .then(res => {
        this.espacos = res.data;
      });
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
      });
  }
}
