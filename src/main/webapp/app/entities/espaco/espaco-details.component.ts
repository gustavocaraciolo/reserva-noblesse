import { Component, Vue, Inject } from 'vue-property-decorator';

import { IEspaco } from '@/shared/model/espaco.model';
import EspacoService from './espaco.service';

@Component
export default class EspacoDetails extends Vue {
  @Inject('espacoService') private espacoService: () => EspacoService;
  public espaco: IEspaco = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.espacoId) {
        vm.retrieveEspaco(to.params.espacoId);
      }
    });
  }

  public retrieveEspaco(espacoId) {
    this.espacoService()
      .find(espacoId)
      .then(res => {
        this.espaco = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
