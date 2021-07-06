import { email, maxLength, minLength, required } from 'vuelidate/lib/validators';
import { Component, Inject, Vue } from 'vue-property-decorator';
import UserManagementService from './user-management.service';
import { IUser, User } from '@/shared/model/user.model';
import ApartamentoService from '@/entities/apartamento/apartamento.service';
import { Apartamento, IApartamento } from '@/shared/model/apartamento.model';
import UserService from '@/admin/user-management/user-management.service';
import TorreService from '@/entities/torre/torre.service';
import { ITorre } from '@/shared/model/torre.model';

const loginValidator = (value: string) => {
  if (!value) {
    return true;
  }
  return /^[a-zA-Z0-9!$&*+=?^_`{|}~.-]+@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$|^[_.@A-Za-z0-9-]+$/.test(value);
};

const validations: any = {
  userAccount: {
    login: {
      required,
      maxLength: maxLength(254),
      pattern: loginValidator,
    },
    firstName: {
      maxLength: maxLength(50),
    },
    lastName: {
      maxLength: maxLength(50),
    },
    email: {
      required,
      email,
      minLength: minLength(5),
      maxLength: maxLength(50),
    },
  },
};

@Component({
  validations,
})
export default class JhiUserManagementEdit extends Vue {
  @Inject('userService') private userManagementService: () => UserManagementService;
  public userAccount: IUser;
  @Inject('apartamentoService') private apartamentoService: () => ApartamentoService;
  public apartamento: IApartamento = new Apartamento();
  @Inject('userService') private userService: () => UserService;
  public users: Array<any> = [];
  @Inject('torreService') private torreService: () => TorreService;

  public torres: ITorre[] = [];
  public apartamentos: IApartamento[] = [];
  public isSaving = false;
  public authorities: any[] = [];
  public languages: any = this.$store.getters.languages;

  beforeRouteEnter(to, from, next) {
    next(vm => {
      vm.initAuthorities();
      if (to.params.userId) {
        vm.init(to.params.userId);
      }
      vm.initRelationships();
    });
  }

  public constructor() {
    super();
    this.userAccount = new User();
    this.userAccount.authorities = [];
  }

  public initAuthorities() {
    this.userManagementService()
      .retrieveAuthorities()
      .then(_res => {
        this.authorities = _res.data;
      });
  }

  public init(userId: number): void {
    this.userManagementService()
      .get(userId)
      .then(res => {
        this.userAccount = res.data;
        this.retrieveApartamento(this.userAccount.id);
      });
  }

  public previousState(): void {
    (<any>this).$router.go(-1);
  }

  public save(): void {
    this.saveUsers();
  }

  public saveUsers(): void {
    this.isSaving = true;
    if (this.userAccount.id) {
      this.userManagementService()
        .update(this.userAccount)
        .then(res => {
          this.saveApartamento(this.userAccount.id);
          this.returnToList();
          this.$root.$bvToast.toast(this.getMessageFromHeader(res).toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Info',
            variant: 'info',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    } else {
      this.userManagementService()
        .create(this.userAccount)
        .then(res => {
          this.saveApartamento(res.data.id);
          this.returnToList();
          this.$root.$bvToast.toast(this.getMessageFromHeader(res).toString(), {
            toaster: 'b-toaster-top-center',
            title: 'Success',
            variant: 'success',
            solid: true,
            autoHideDelay: 5000,
          });
        });
    }
  }

  public saveApartamento(UserId): void {
    this.isSaving = true;
    this.apartamento.user = this.userAccount;
    this.apartamento.user.id = UserId;
    this.apartamentoService()
      .update(this.apartamento)
      .then(param => {});
  }

  private returnToList(): void {
    this.isSaving = false;
    (<any>this).$router.go(-1);
  }

  private getMessageFromHeader(res: any): any {
    return this.$t(res.headers['x-reservanoblesseapp-alert'], {
      param: decodeURIComponent(res.headers['x-reservanoblesseapp-params'].replace(/\+/g, ' ')),
    });
  }

  public initRelationships(): void {
    this.torreService()
      .retrieve()
      .then(res => {
        this.torres = res.data;
      });
    this.apartamentoService()
      .retrieve()
      .then(res => {
        this.apartamentos = res.data;
      });
  }

  public retrieveApartamento(UserId): void {
    this.apartamentoService()
      .findByUserId(UserId)
      .then(res => {
        this.apartamento = res;
      });
  }
}
