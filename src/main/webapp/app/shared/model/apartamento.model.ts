import { IUser } from '@/shared/model/user.model';
import { ITorre } from '@/shared/model/torre.model';

export interface IApartamento {
  id?: number;
  numero?: number | null;
  user?: IUser | null;
  torres?: ITorre[] | null;
}

export class Apartamento implements IApartamento {
  constructor(public id?: number, public numero?: number | null, public user?: IUser | null, public torres?: ITorre[] | null) {}
}
