import { Authority } from '@/shared/security/authority';
/* tslint:disable */
// prettier-ignore

// prettier-ignore
const Tenis = () => import('@/entities/tenis/tenis.vue');
// prettier-ignore
const TenisUpdate = () => import('@/entities/tenis/tenis-update.vue');
// prettier-ignore
const TenisDetails = () => import('@/entities/tenis/tenis-details.vue');
// jhipster-needle-add-entity-to-router-import - JHipster will import entities to the router here

export default [
  {
    path: '/tenis',
    name: 'Tenis',
    component: Tenis,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/tenis/new',
    name: 'TenisCreate',
    component: TenisUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/tenis/:tenisId/edit',
    name: 'TenisEdit',
    component: TenisUpdate,
    meta: { authorities: [Authority.USER] },
  },
  {
    path: '/tenis/:tenisId/view',
    name: 'TenisView',
    component: TenisDetails,
    meta: { authorities: [Authority.USER] },
  },
  // jhipster-needle-add-entity-to-router - JHipster will add entities to the router here
];
