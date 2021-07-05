import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITorre } from '@/shared/model/torre.model';
import TorreService from './torre.service';

@Component
export default class TorreDetails extends Vue {
  @Inject('torreService') private torreService: () => TorreService;
  public torre: ITorre = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.torreId) {
        vm.retrieveTorre(to.params.torreId);
      }
    });
  }

  public retrieveTorre(torreId) {
    this.torreService()
      .find(torreId)
      .then(res => {
        this.torre = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
