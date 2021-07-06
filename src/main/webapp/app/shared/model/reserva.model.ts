import { IEspaco } from '@/shared/model/espaco.model';
import { IUser } from '@/shared/model/user.model';

export interface IReserva {
  id?: number;
  dataHora?: Date;
  notas?: string | null;
  espacos?: IEspaco[] | null;
  user?: IUser | null;
}

export class Reserva implements IReserva {
  constructor(
    public id?: number,
    public dataHora?: Date,
    public notas?: string | null,
    public espacos?: IEspaco[] | null,
    public user?: IUser | null
  ) {}
}
