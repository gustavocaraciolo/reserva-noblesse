import { Component, Vue, Inject } from 'vue-property-decorator';

import { IReserva } from '@/shared/model/reserva.model';
import ReservaService from './reserva.service';

@Component
export default class ReservaDetails extends Vue {
  @Inject('reservaService') private reservaService: () => ReservaService;
  public reserva: IReserva = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.reservaId) {
        vm.retrieveReserva(to.params.reservaId);
      }
    });
  }

  public retrieveReserva(reservaId) {
    this.reservaService()
      .find(reservaId)
      .then(res => {
        this.reserva = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
