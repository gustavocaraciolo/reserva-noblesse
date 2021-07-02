import { IUser } from '@/shared/model/user.model';

export interface ITenis {
  id?: number;
  date?: Date;
  notes?: string | null;
  user?: IUser | null;
}

export class Tenis implements ITenis {
  constructor(public id?: number, public date?: Date, public notes?: string | null, public user?: IUser | null) {}
}
