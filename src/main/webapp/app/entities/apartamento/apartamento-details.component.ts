import { Component, Vue, Inject } from 'vue-property-decorator';

import { IApartamento } from '@/shared/model/apartamento.model';
import ApartamentoService from './apartamento.service';

@Component
export default class ApartamentoDetails extends Vue {
  @Inject('apartamentoService') private apartamentoService: () => ApartamentoService;
  public apartamento: IApartamento = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.apartamentoId) {
        vm.retrieveApartamento(to.params.apartamentoId);
      }
    });
  }

  public retrieveApartamento(apartamentoId) {
    this.apartamentoService()
      .find(apartamentoId)
      .then(res => {
        this.apartamento = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
