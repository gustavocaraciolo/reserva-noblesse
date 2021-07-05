import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Espaco = () => import('@/entities/espaco/espaco.vue');
// prettier-ignore
const EspacoUpdate = () => import('@/entities/espaco/espaco-update.vue');
// prettier-ignore
const EspacoDetails = () => import('@/entities/espaco/espaco-details.vue');
// prettier-ignore
const Reserva = () => import('@/entities/reserva/reserva.vue');
// prettier-ignore
const ReservaUpdate = () => import('@/entities/reserva/reserva-update.vue');
// prettier-ignore
const ReservaDetails = () => import('@/entities/reserva/reserva-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/espaco',
    name: 'Espaco',
    component: Espaco,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/espaco/new',
    name: 'EspacoCreate',
    component: EspacoUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/espaco/:espacoId/edit',
    name: 'EspacoEdit',
    component: EspacoUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/espaco/:espacoId/view',
    name: 'EspacoView',
    component: EspacoDetails,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/reserva',
    name: 'Reserva',
    component: Reserva,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/reserva/new',
    name: 'ReservaCreate',
    component: ReservaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/reserva/:reservaId/edit',
    name: 'ReservaEdit',
    component: ReservaUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/reserva/:reservaId/view',
    name: 'ReservaView',
    component: ReservaDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
