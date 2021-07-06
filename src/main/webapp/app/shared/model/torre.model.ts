import { IApartamento } from '@/shared/model/apartamento.model';

export interface ITorre {
  id?: number;
  nome?: string | null;
  apartamentos?: IApartamento[] | null;
}

export class Torre implements ITorre {
  constructor(public id?: number, public nome?: string | null, public apartamentos?: IApartamento[] | null) {}
}
