/* tslint:disable max-line-length */
import { shallowMount, createLocalVue, Wrapper } from '@vue/test-utils';
import sinon, { SinonStubbedInstance } from 'sinon';
import Router from 'vue-router';

import * as config from '@/shared/config/config';
import EspacoUpdateComponent from '@/entities/espaco/espaco-update.vue';
import EspacoClass from '@/entities/espaco/espaco-update.component';
import EspacoService from '@/entities/espaco/espaco.service';

import ReservaService from '@/entities/reserva/reserva.service';

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
  describe('Espaco Management Update Component', () => {
    let wrapper: Wrapper<EspacoClass>;
    let comp: EspacoClass;
    let espacoServiceStub: SinonStubbedInstance<EspacoService>;

    beforeEach(() => {
      espacoServiceStub = sinon.createStubInstance<EspacoService>(EspacoService);

      wrapper = shallowMount<EspacoClass>(EspacoUpdateComponent, {
        store,
        i18n,
        localVue,
        router,
        provide: {
          espacoService: () => espacoServiceStub,

          reservaService: () => new ReservaService(),
        },
      });
      comp = wrapper.vm;
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', async () => {
        // GIVEN
        const entity = { id: 123 };
        comp.espaco = entity;
        espacoServiceStub.update.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(espacoServiceStub.update.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', async () => {
        // GIVEN
        const entity = {};
        comp.espaco = entity;
        espacoServiceStub.create.resolves(entity);

        // WHEN
        comp.save();
        await comp.$nextTick();

        // THEN
        expect(espacoServiceStub.create.calledWith(entity)).toBeTruthy();
        expect(comp.isSaving).toEqual(false);
      });
    });

    describe('Before route enter', () => {
      it('Should retrieve data', async () => {
        // GIVEN
        const foundEspaco = { id: 123 };
        espacoServiceStub.find.resolves(foundEspaco);
        espacoServiceStub.retrieve.resolves([foundEspaco]);

        // WHEN
        comp.beforeRouteEnter({ params: { espacoId: 123 } }, null, cb => cb(comp));
        await comp.$nextTick();

        // THEN
        expect(comp.espaco).toBe(foundEspaco);
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
