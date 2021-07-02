import { Component, Vue, Inject } from 'vue-property-decorator';

import { ITenis } from '@/shared/model/tenis.model';
import TenisService from './tenis.service';

@Component
export default class TenisDetails extends Vue {
  @Inject('tenisService') private tenisService: () => TenisService;
  public tenis: ITenis = {};

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tenisId) {
        vm.retrieveTenis(to.params.tenisId);
      }
    });
  }

  public retrieveTenis(tenisId) {
    this.tenisService()
      .find(tenisId)
      .then(res => {
        this.tenis = res;
      });
  }

  public previousState() {
    this.$router.go(-1);
  }
}
