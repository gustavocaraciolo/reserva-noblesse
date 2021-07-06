import { IUser } from '@/shared/model/user.model';
import { IEspaco } from '@/shared/model/espaco.model';

export interface IReserva {
  id?: number;
  dataHora?: Date;
  notas?: string | null;
  user?: IUser | null;
  espaco?: IEspaco | null;
}

export class Reserva implements IReserva {
  constructor(
    public id?: number,
    public dataHora?: Date,
    public notas?: string | null,
    public user?: IUser | null,
    public espaco?: IEspaco | null
  ) {}
}
