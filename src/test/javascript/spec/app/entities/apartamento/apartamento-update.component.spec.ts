/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import ApartamentoUpdateComponent from '@/entities/apartamento/apartamento-update.vue';
import ApartamentoClass from '@/entities/apartamento/apartamento-update.component';
import ApartamentoService from '@/entities/apartamento/apartamento.service';

import TorreService from '@/entities/torre/torre.service';

import UserService from '@/admin/user-management/user-management.service';

const localVue = createLocalVue();

config.initVueApp(localVue);
const i18n = config.initI18N(localVue);
const store = config.initVueXStore(localVue);
const router = new Router();
localVue.use(Router);
localVue.component('font-awesome-icon', {});
localVue.component('b-input-group', {});
localVue.component('b-input-group-prepend', {});
localVue.component('b-form-datepicker', {});
localVue.component('b-form-input', {});

describe('Component Tests', () => {
  describe('Apartamento Management Update Component', () => {
    let wrapper: Wrapper<ApartamentoClass>;
    let comp: ApartamentoClass;
    let apartamentoServiceStub: SinonStubbedInstance<ApartamentoService>;

    beforeEach(() => {
      apartamentoServiceStub = sinon.createStubInstance<ApartamentoService>(ApartamentoService);

      wrapper = shallowMount<ApartamentoClass>(ApartamentoUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          apartamentoService: () => apartamentoServiceStub,

          torreService: () => new TorreService(),

          userService: () => new UserService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.apartamento = entity;
        apartamentoServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(apartamentoServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.apartamento = entity;
        apartamentoServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(apartamentoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundApartamento = { id: 123 };
        apartamentoServiceStub.find.resolves(foundApartamento);
        apartamentoServiceStub.retrieve.resolves([foundApartamento]);

        // WHEN
        comp.beforeRouteEnter({ params: { apartamentoId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.apartamento).toBe(foundApartamento);
      });
    });

    describe('Previous state', () => {
      it('Should go previous state', async () => {
        comp.previousState();
        await comp.$nextTick();

        expect(comp.$router.currentRoute.fullPath).toContain('/');
      });
    });
  });
});
