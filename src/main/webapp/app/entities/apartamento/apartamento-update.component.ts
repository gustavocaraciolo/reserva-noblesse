import { Component, Vue, Inject } from 'vue-property-decorator';

import TorreService from '@/entities/torre/torre.service';
import { ITorre } from '@/shared/model/torre.model';

import UserService from '@/admin/user-management/user-management.service';

import { IApartamento, Apartamento } from '@/shared/model/apartamento.model';
import ApartamentoService from './apartamento.service';

const validations: any = {
  apartamento: {
    numero: {},
  },
};

@Component({
  validations,
})
export default class ApartamentoUpdate extends Vue {
  @Inject('apartamentoService') private apartamentoService: () => ApartamentoService;
  public apartamento: IApartamento = new Apartamento();

  @Inject('torreService') private torreService: () => TorreService;

  public torres: ITorre[] = [];

  @Inject('userService') private userService: () => UserService;

  public users: Array<any> = [];
  public isSaving = false;
  public currentLanguage = '';

  beforeRouteEnter(to, from, next) {
    next(vm => {
      if (to.params.apartamentoId) {
        vm.retrieveApartamento(to.params.apartamentoId);
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
    this.apartamento.users = [];
  }

  public save(): void {
    this.isSaving = true;
    if (this.apartamento.id) {
      this.apartamentoService()
        .update(this.apartamento)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('reservaNoblesseApp.apartamento.updated', { param: param.id });
          return this.$root.$bvToast.toast(message.toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.apartamentoService()
        .create(this.apartamento)
        .then(param => {
          this.isSaving = false;
          this.$router.go(-1);
          const message = this.$t('reservaNoblesseApp.apartamento.created', { param: param.id });
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

  public retrieveApartamento(apartamentoId): void {
    this.apartamentoService()
      .find(apartamentoId)
      .then(res => {
        this.apartamento = res;
      });
  }

  public previousState(): void {
    this.$router.go(-1);
  }

  public initRelationships(): void {
    this.torreService()
      .retrieve()
      .then(res => {
        this.torres = res.data;
      });
    this.userService()
      .retrieve()
      .then(res => {
        this.users = res.data;
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
