import { IUser } from '@/shared/model/user.model';
import { IEspaco } from '@/shared/model/espaco.model';

export interface IReserva {
  id?: number;
  date?: Date;
  notes?: string | null;
  user?: IUser | null;
  espacos?: IEspaco[] | null;
}

export class Reserva implements IReserva {
  constructor(
    public id?: number,
    public date?: Date,
    public notes?: string | null,
    public user?: IUser | null,
    public espacos?: IEspaco[] | null
  ) {}
}
