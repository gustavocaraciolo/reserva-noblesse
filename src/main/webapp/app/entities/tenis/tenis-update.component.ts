import { Component, Vue, Inject } from 'vue-property-decorator';

import { required, maxLength } from 'vuelidate/lib/validators';

import UserService from '@/admin/user-management/user-management.service';

import { ITenis, Tenis } from '@/shared/model/tenis.model';
import TenisService from './tenis.service';

const validations: any = {
  tenis: {
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
export default class TenisUpdate extends Vue {
  @Inject('tenisService') private tenisService: () => TenisService;
  public tenis: ITenis = new Tenis();

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.tenisId) {
        vm.retrieveTenis(to.params.tenisId);
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
    if (this.tenis.id) {
      this.tenisService()
        .update(this.tenis)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('reservaNoblesseApp.tenis.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.tenisService()
        .create(this.tenis)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('reservaNoblesseApp.tenis.created', { param: param.id });
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

  public retrieveTenis(tenisId): void {
    this.tenisService()
      .find(tenisId)
      .then(res => {
        this.tenis = res;
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
  }
}
