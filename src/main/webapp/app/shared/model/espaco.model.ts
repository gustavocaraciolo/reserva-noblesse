import { IReserva } from '@/shared/model/reserva.model';

export interface IEspaco {
  id?: number;
  nome?: string | null;
  reserva?: IReserva | null;
}

export class Espaco implements IEspaco {
  constructor(public id?: number, public nome?: string | null, public reserva?: IReserva | null) {}
}
